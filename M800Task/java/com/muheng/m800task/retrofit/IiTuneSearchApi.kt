package com.muheng.m800task.retrofit

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Muheng Li on 2018/8/16.
 */
interface IiTuneSearchApi {

    companion object {
        const val API_BASE_RUL = "https://itunes.apple.com/"

        // Node
        const val SEARCH = "search"

        // Parameter Keys
        const val TERM = "term"
        const val MEDIA = "media"
        const val ENTITY = "entity"
        const val ATTRIBUTE = "attribute"
        const val LIMIT = "limit"

        // Constants
        const val LIMIT_MAX = 200
        const val TRACK = "track"

        fun getParamsMap(params: ArrayList<Parameter>): Map<String, String> {
            var paramsMap = HashMap<String, String>()

            paramsMap.put(IiTuneSearchApi.MEDIA, "music")
            paramsMap.put(IiTuneSearchApi.LIMIT, LIMIT_MAX.toString())
            for (p in params) {
                if (p.value != null) {
                    paramsMap[p.key] = p.value as String
                }
            }

            return paramsMap
        }
    }

    @GET(SEARCH)
    fun search(@QueryMap params: Map<String, String>): Observable<ResponseResult>
}