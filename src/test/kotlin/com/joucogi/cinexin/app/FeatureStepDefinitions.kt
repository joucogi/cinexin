package com.joucogi.cinexin.app

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@CucumberContextConfiguration
@SpringBootTest
class FeatureStepDefinitions {
    @Autowired
    private lateinit var mvc: MockMvc
    private var actualResponse: ResultActionsDsl? = null

    @Given("I send a {string} request to {string}")
    fun `I send a request to`(method: String, url: String) {
        actualResponse = mvc.get(url) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }
    }

    @Then("the response status code should be {int}")
    fun `the response content should be OK`(expectedStatusCode: Int) {
        when (expectedStatusCode) {
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
        }
    }

    @Then("the response content should be")
    fun `the response content should be`(bodyResponse: String) {
        actualResponse!!.andExpect {
            content {
                contentType(MediaType.APPLICATION_JSON)
                json(bodyResponse)
            }
        }
    }

    @Then("the response content should be empty")
    fun `the response content should be empty`() {
        actualResponse!!.andExpect {
            content {
                string("")
            }
        }
    }
}