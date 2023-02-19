package com.example.cupcake.test

import android.icu.util.Calendar
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import java.text.SimpleDateFormat
import java.util.Locale


class CupcakeScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    private fun getFormattedDate(): String{
        val calendar = Calendar.getInstance()
        calendar.add(java.util.Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())

        return formatter.format(calendar.time)
    }
    private fun navigateToPickupScreen(){
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next).performClick()
    }

    private fun navigateToSummaryScreen(){
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate()).performClick()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next).performClick()
    }
    private fun perFormNavigateUp(){
        val backText = composeTestRule.activity.getString(com.example.cupcake.R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }
    @Before
    fun setupCupcakeNavHost() {
        composeTestRule.setContent {
            navController =
                TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            CupcakeApp(navHostController = navController)
        }
    }
    @Test
    fun cupCakeNavHost_verifyStartDestination(){
        assertEquals(CupcakeScreen.Start.name, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        val backText = composeTestRule.activity.getString(com.example.cupcake.R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen(){
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.one_cupcake).performClick()
    }

    @Test
    fun navigateToFlavorScreen() {
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.one_cupcake)
            .performClick()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.chocolate)
            .performClick()
    }

    @Test
    fun cupcakeNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }
//TASK
//    Navigating to the Start screen by clicking the Up button from the Flavor screen
//    Navigating to the Start screen by clicking the Cancel button from the Flavor screen
//    Navigating to the Pickup screen
//    Navigating to the Flavor screen by clicking the Up button from the Pickup screen
//    Navigating to the Start screen by clicking the Cancel button from the Pickup screen
//    Navigating to the Summary screen
//    Navigating to the Start screen by clicking the Cancel button from the Summary screen
    @Test
    fun cupcakeNavHost_navigateStart_byUpButtonFlavorScreen(){
        navigateToFlavorScreen()
        perFormNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_navigateStart_byCancelButtonFlavorScreen(){
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_navigatePickUp(){
        navigateToPickupScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }
    @Test
    fun cupcakeNavHost_navigateFlavorScreen_byUpButtonPickUp(){
        navigateToPickupScreen()
        perFormNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }
    @Test
    fun cupcakeNavHost_navigateStartScreen_byCancelPickUp(){
        navigateToPickupScreen()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }
    @Test
    fun cupcakeNavHost_navigateSummaryScreen(){
        navigateToSummaryScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }
    @Test
    fun cupcakeNavHost_navigateStart_byCancelSummary(){
        navigateToSummaryScreen()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }
}