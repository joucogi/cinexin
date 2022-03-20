package com.cinexin.apps.shared.infrastructure.cucumber

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get

class FeatureApiContext {
    fun sendRequest(mvc: MockMvc, method: String, url: String) = when (method) {
        "GET" -> mvc.get(url) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }
        else  -> throw RuntimeException("Method not available")
    }

    fun responseCodeShouldBe(actualResponse: ResultActionsDsl?, expectedStatusCode: Int) = when (expectedStatusCode) {
        200 -> actualResponse!!.andExpect {
            status {
                isOk()
            }
        }
        201 -> actualResponse!!.andExpect {
            status {
                isCreated()
            }
        }
        else -> throw RuntimeException("Unknown actual response code")
    }

    fun responseContentShouldBe(actualResponse: ResultActionsDsl?, bodyResponse: String) = actualResponse!!.andExpect {
        content {
            contentType(MediaType.APPLICATION_JSON)
            json(bodyResponse)
        }
    }

    fun responseContentShouldBeEmpty(actualResponse: ResultActionsDsl?) = actualResponse!!.andExpect {
        content {
            string("")
        }
    }
}