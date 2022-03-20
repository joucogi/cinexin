package com.cinexin.apps.backoffice.infrastructure.cucumber

import com.cinexin.apps.backoffice.BackofficeApplication
import com.cinexin.apps.shared.infrastructure.cucumber.FeatureApiContext
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl

@AutoConfigureMockMvc
@CucumberContextConfiguration
@SpringBootTest(classes = [BackofficeApplication::class])
class StepDefinitions(
    private val context: FeatureApiContext = FeatureApiContext()
) {
    @Autowired
    private lateinit var mvc: MockMvc
    private var actualResponse: ResultActionsDsl? = null

    @Given("I send a {string} request to {string}")
    fun `I send a request to`(method: String, url: String) {
        actualResponse = context.sendRequest(mvc, method, url)
    }

    @Then("the response status code should be {int}")
    fun `the response content should be OK`(expectedStatusCode: Int) {
        context.responseCodeShouldBe(actualResponse, expectedStatusCode)
    }

    @Then("the response content should be")
    fun `the response content should be`(bodyResponse: String) {
        context.responseContentShouldBe(actualResponse, bodyResponse)
    }

    @Then("the response content should be empty")
    fun `the response content should be empty`() {
        context.responseContentShouldBeEmpty(actualResponse)
    }
}