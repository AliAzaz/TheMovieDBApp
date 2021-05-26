package com.example.themoviedbapp.di.auth

import com.example.themoviedbapp.utils.CONSTANTS

/**
 * @author AliAzazAlam on 5/16/2021.
 */
object ApiRoutes {
    const val GET_MOVIES_FIRST = "movie/"
    const val GET_MOVIES_SECOND = "?api_key=${CONSTANTS.API_KEY}"
    const val GET_MOVIES_LIST = GET_MOVIES_FIRST + "changes" + GET_MOVIES_SECOND
}