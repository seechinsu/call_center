package modules


import com.google.inject.{AbstractModule, Singleton}
import repositories._

class ApplicationModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[PrestoStore]).to(classOf[PrestoStoreImpl]).in(classOf[Singleton])
    bind(classOf[WarehouseStore]).to(classOf[WarehouseStoreImpl]).in(classOf[Singleton])
  }

}
