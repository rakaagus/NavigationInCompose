package com.dicoding.jetreward.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.dicoding.jetreward.model.OrderReward
import com.dicoding.jetreward.model.Reward
import org.junit.Assert.*
import org.junit.Rule
import com.dicoding.jetreward.R
import com.dicoding.jetreward.onNodeWithStringId
import com.dicoding.jetreward.ui.theme.JetRewardTheme
import org.junit.Before
import org.junit.Test

class DetailScreenKtTest{

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeOrderReward = OrderReward(
        reward = Reward(4, R.drawable.reward_4, "Jaket Hoodie Dicoding", 7500),
        count = 0
    )

    @Before
    fun setUp(){
        composeRule.setContent {
            JetRewardTheme {
                DetailContent(
                    fakeOrderReward.reward.image,
                    fakeOrderReward.reward.title,
                    fakeOrderReward.reward.requiredPoint,
                    fakeOrderReward.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed(){
        composeRule.onNodeWithText(fakeOrderReward.reward.title).assertIsDisplayed()
        composeRule.onNodeWithText(
            composeRule.activity.getString(
                R.string.required_point,
                fakeOrderReward.reward.requiredPoint
            )
        ).assertIsDisplayed()
    }

    @Test
    fun increaseProduct_buttonEnabled(){
        composeRule.onNodeWithContentDescription("Order Button").assertIsDisplayed()
        composeRule.onNodeWithStringId(
            R.string.plus_symbol
        ).performClick()
        composeRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_correctCounter(){
        composeRule.onNodeWithStringId(R.string.plus_symbol).performClick().performClick()
        composeRule.onNodeWithTag("count").assert(hasText("2"))
    }

    @Test
    fun decreaseProduct_stillZero(){
        composeRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeRule.onNodeWithTag("count").assert(hasText("0"))
    }
}