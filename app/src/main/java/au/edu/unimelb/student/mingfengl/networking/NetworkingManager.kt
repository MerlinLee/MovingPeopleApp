package au.edu.unimelb.student.mingfengl.networking

import okhttp3.*


class NetworkingManager private constructor(){
    companion object{
        val instance: NetworkingManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            NetworkingManager()
        }
    }
    private val okHttpClient : OkHttpClient
    private val cookieStore = HashMap<String,List<Cookie>>()

    init {
        okHttpClient = OkHttpClient.Builder().cookieJar(
            object : CookieJar{
                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    var cookies = cookieStore.get(url.host)
                    return if(cookies !=null)cookies else ArrayList<Cookie>()
                }

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    cookieStore.put(url.host, cookies);
                }

            }
        ).build()
    }

    fun send(request: Request) : Response{
        return okHttpClient.newCall(request).execute()
    }

}