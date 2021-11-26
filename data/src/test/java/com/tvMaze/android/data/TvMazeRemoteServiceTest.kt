package com.tvMaze.android.data


import com.tvMaze.android.data.remote.service.TvMazeRemoteService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail


@RunWith(JUnit4::class)
class TvMazeRemoteServiceTest {

    private val mockWebServer = MockWebServer()
    private lateinit var tvMazeRemoteService: TvMazeRemoteService
    private val query = "girl"


    @Before
    fun setUp() {
        mockWebServer.start()
        mockWebServer.dispatcher = setUpMockWebServerDispatcher()
        setUpTvShowRetrofitService()

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `read sample success json file`() {
        val fileContent = FileReader.readFileFromResources("tv_show.json")
        assertNotNull(fileContent)
    }

    @Test
    fun `Assert remote service returns data`(): Unit =
        runBlocking {
            val shows =
                tvMazeRemoteService.searchShows(query)

            assertNotNull(shows)
        }

    @Test
    fun `Assert remote service response match JSON Server response`() = runBlocking {
        val tvShows = tvMazeRemoteService.searchShows(
             query
        )
        assertEquals(
            TvShowData.provideRemoteTvShowsFromAssets().size,
            tvShows.size
        )

        assertEquals(
            TvShowData.provideRemoteTvShowsFromAssets()[0],
            tvShows[0]
        )
    }

    private fun setUpTvShowRetrofitService() {
        tvMazeRemoteService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(TvMazeRemoteService::class.java)
    }

    private fun setUpMockWebServerDispatcher(): Dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/search/shows?q=${query}" -> {
                    MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(FileReader.readFileFromResources("tv_show.json"))
                }

                else ->MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)

            }
        }
    }

}