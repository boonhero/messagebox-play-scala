package controllers

import model.Message
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

class Application extends Controller {

  implicit val messageFormat = Json.format[Message]

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createBox = Action {
    Ok(views.html.createBox("Create your box"))
  }

  val messageForm = Form(
    mapping(
      "id" -> ignored("": String),
      "message" -> nonEmptyText
    )(Message.apply)(Message.unapply)
  )

  /**
   * Retrieves all messages
   */
  def messageList = Action { implicit request =>
    Logger.info("message list")
    val json: JsValue = Json.toJson(Message.findAll())
    Logger.info(s"json result:  [${json}]")
    Ok(json)
  }

  /**
   * Handle Message submission form
   */
  def saveMessage = Action(BodyParsers.parse.json) { implicit request =>
    Logger.info("saveMessage")
    val b = request.body.validate[Message]
    b.fold(
      errors => {
        BadRequest("")
      },
      message => {
        val msgId: Option[String] = Message.insert(message)
        Logger.info(s"new message added: ${msgId.getOrElse("Not found")}")
        Ok("")
      }
    )
  }

}
