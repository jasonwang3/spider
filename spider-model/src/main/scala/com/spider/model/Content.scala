package com.spider.model

/**
  * Created by jason on 16-11-14.
  */
@SerialVersionUID(1L)
class Content(val name: String, var data: Any, val urlDownload: Boolean) extends Serializable{


  override def toString = s"Content($name, $data, $urlDownload)"
}
