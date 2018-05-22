package client

import scala.concurrent.Future

trait ConsumerRecordProcessor {
  def process(record: InputEvent): Unit = ???
  def processAsync(record: InputEvent): Future[Unit] = ???
}
