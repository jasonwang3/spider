package downloader

import com.spider.model.{Site, Task}
import com.sun.javafx.tk.Toolkit.Task

/**
  * Created by jason on 16-1-28.
  */
object Test extends App{
  val task = new Site().setDomain("localhost").toTask()
  print(task)
}
