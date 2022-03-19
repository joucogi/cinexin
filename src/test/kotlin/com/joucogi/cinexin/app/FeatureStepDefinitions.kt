package com.joucogi.cinexin.app

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.Assertions
import org.springframework.boot.test.context.SpringBootTest

@CucumberContextConfiguration
@SpringBootTest
class FeatureStepDefinitions {
    var initialBalance: Double? = null
    var initialCredited: Double? = null
    var actualBalance: Double? = null

    @Given("account balance is {double}")
    fun givenAccountBalance(initialBalance: Double?) {
        this.initialBalance = initialBalance
    }

    @When("the account is credited with {double}")
    fun whenAccountIsCreditedWith(initialCredited: Double?) {
        this.initialCredited = initialCredited
    }

    @Then("account should have a balance of {double}")
    fun thenAccountShouldHaveABalanceOf(actualBalance: Double?) {
        this.actualBalance = actualBalance
        Assertions.assertEquals(this.actualBalance, initialBalance!! + initialCredited!!)
    }
}