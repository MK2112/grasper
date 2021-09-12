package com.mk2112

import com.mk2112.businesslogic.controller.AbstractGrasperController
import com.mk2112.businesslogic.controller.GrasperController

fun main(args: Array<String>) {
    val controller: AbstractGrasperController = GrasperController()
    controller.start(args)
}

