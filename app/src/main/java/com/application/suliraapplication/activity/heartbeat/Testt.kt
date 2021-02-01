package com.application.suliraapplication.activity.heartbeat

import io.reactivex.subjects.BehaviorSubject

object Testt {

    var bootsUpdatedList: ArrayList<String> = ArrayList()
    set(bootsList) {
        field = bootsList
        updateBootsListSubject.onNext(bootsUpdatedList)
    }

    val updateBootsListSubject: BehaviorSubject<ArrayList<String>> = BehaviorSubject.create()
}