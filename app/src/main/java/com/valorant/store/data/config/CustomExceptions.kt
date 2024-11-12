package com.valorant.store.data.config

import kotlin.reflect.KClass

class RemoteDataFailure(clazz: KClass<*>) :
    RuntimeException("Failure getting data in ${clazz.simpleName}") // TODO: Tech Debt remove?
