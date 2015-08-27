package model

import play.api.libs.json.Json

object Messages {
  val messages: List[Message] = List()
}

case class Message(var id: String, message: String)


/**
 * Helper for pagination
 */
case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}

object Message {

  def insert(message: Message) = Option[String] {
    val uuid: String = java.util.UUID.randomUUID.toString
    message.id = uuid
    Messages.messages ::: List(message)

    uuid
  }

  def findAll(): List[Message] = Messages.messages

}
