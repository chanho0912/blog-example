package com.noah.springdata.blog.serviceprovider

interface SampleInterface {

    fun sampling(): String
}

class SampleInterfaceImpl : SampleInterface {
    override fun sampling(): String {
        return "implementation"
    }
}

class OtherInterfaceImpl : SampleInterface {
    override fun sampling(): String {
        return "another implementation"
    }
}
