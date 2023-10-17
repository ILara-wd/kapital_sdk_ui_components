@file:OptIn(ExperimentalComposeUiApi::class)

package com.kapital.kapitalApp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.models.BarPlotData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.combinedchart.CombinedChart
import co.yml.charts.ui.combinedchart.model.CombinedChartData
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.biometrics.BiometricsResultType
import com.kapital.kapitalApp.compose.DefaultPreview
import com.kapital.kapitalApp.compose.components.AutoSizeTextField
import com.kapital.kapitalApp.compose.components.CodeTextFields
import com.kapital.kapitalApp.compose.components.EmailItem
import com.kapital.kapitalApp.compose.components.EmailViewModel
import com.kapital.kapitalApp.compose.components.KapitalButton
import com.kapital.kapitalApp.compose.components.KapitalConfirmationDialog
import com.kapital.kapitalApp.compose.components.KapitalDialog
import com.kapital.kapitalApp.compose.components.KapitalDropDownList
import com.kapital.kapitalApp.compose.components.KapitalLogoComponent
import com.kapital.kapitalApp.compose.components.KapitalOutlinedButton
import com.kapital.kapitalApp.compose.components.KapitalPdfComponent
import com.kapital.kapitalApp.compose.components.KapitalPhoneTextField
import com.kapital.kapitalApp.compose.components.KapitalTextField
import com.kapital.kapitalApp.compose.components.KapitalTopAppBar
import com.kapital.kapitalApp.compose.components.LinkText
import com.kapital.kapitalApp.compose.components.LinkTextData
import com.kapital.kapitalApp.compose.components.mask.CurrencyAmountInputVisualTransformation
import com.kapital.kapitalApp.compose.components.mask.EmptyTextToolbar
import com.kapital.kapitalApp.compose.components.viewmodel.BiometricViewModel
import com.kapital.kapitalApp.compose.components.viewmodel.LoginError
import com.kapital.kapitalApp.compose.components.viewmodel.PersonalInfoErrors
import com.kapital.kapitalApp.compose.theme.FunBlue50
import com.kapital.kapitalApp.compose.theme.KapitalTheme
import com.kapital.kapitalApp.compose.theme.RegentGray
import com.kapital.kapitalApp.compose.theme.getSimpleColorIcons
import com.kapital.kapitalApp.compose.utils.kapitalCommonBottomPadding
import com.kapital.kapitalApp.compose.utils.kapitalCommonHorizontalPadding
import com.kapital.kapitalApp.compose.utils.kapitalCommonTopPadding
import com.kapital.kapitalApp.tools.findActivity
import com.kapital.kapitalApp.tools.getIconPassword
import com.kapital.kapitalApp.tools.initBiometric

class MainActivity : AppCompatActivity() {//ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KapitalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EmailApp(emailViewModel: EmailViewModel = viewModel()) {
    // Collect the state of messages from the view model
    val messages by emailViewModel.messagesState.collectAsState()

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Email App") },
                    actions = {
                        // Refresh button
                        IconButton(onClick = emailViewModel::refresh) {
                            Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp),
            ) {
                itemsIndexed(
                    items = messages,
                    // Provide a unique key based on the email content
                    key = { _, item -> item.hashCode() }
                ) { _, emailContent ->
                    // Display each email item
                    EmailItem(emailContent, onRemove = emailViewModel::removeItem)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("LongMethod", "FunctionNaming", "CyclomaticComplexMethod")// Only Test
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val onSwipe = remember { mutableStateOf(value = true) }
    val onLoadingPDF = remember { mutableStateOf(value = false) }
    val onLoadingData = remember { mutableStateOf(value = false) }
    val onShowDialog = remember { mutableStateOf(value = false) }
    val onShowSecondDialog = remember { mutableStateOf(value = false) }
    val showDialog = remember { mutableStateOf(value = false) }
    val successBiometrics = remember { mutableStateOf(value = false) }
    val viewModel = hiltViewModel<BiometricViewModel>()
    val phoneState by viewModel.capturedPhone.collectAsState()
    val focusManager = LocalFocusManager.current
    val currentCategoryValue by viewModel.filterStore.valueState.collectAsState()
    val errorName by viewModel.nameState.error.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val ctx = LocalContext.current
    val mail = stringResource(id = R.string.faq_mail)
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    if (onSwipe.value) {
        EmailApp()
    } else if (onLoadingPDF.value) {
        KapitalPdfComponent(R.raw.cap_f_73)
    } else if (onLoadingData.value) {
        DefaultPreview()
        //Column(
        //    modifier = Modifier.fillMaxSize(),
        //    horizontalAlignment = Alignment.CenterHorizontally,
        //    verticalArrangement = Arrangement.Center
        //) {
        //    KapitalProgressIndicator()
        //}
    } else {
        Scaffold(topBar = {
            KapitalTopAppBar(title = stringResource(id = R.string.app_name),
                showLeftButton = true,
                showRightButton = true,
                back = { ctx.findActivity()?.onBackPressed() },
                changeStateRightIcon = successBiometrics.value,
                rightAction = { showDialog.value = true })
        }, modifier = Modifier.kapitalCommonBottomPadding(), content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(state)
            ) {
                KapitalLogoComponent()

                ChartDesign()

                Text(
                    text = "Hello $name",
                    modifier = modifier,
                    style = MaterialTheme.typography.headlineLarge
                )

                KapitalDropDownList(
                    label = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(id = R.dimen.normal_padding),
                            end = dimensionResource(id = R.dimen.normal_padding),
                            start = dimensionResource(id = R.dimen.normal_padding)
                        ),
                    list = viewModel.categories.value,
                    placeHolder = stringResource(id = R.string.select_category),
                    selectedObject = viewModel.filterStore::update,
                    value = currentCategoryValue,
                    onClick = {},
                )

                Text(
                    text = "Hello head line Large",
                    modifier = modifier,
                    style = MaterialTheme.typography.headlineLarge
                )/*
                                        Text(
                                            text = "Hello head line Medium",
                                            modifier = modifier,
                                            style = MaterialTheme.typography.headlineMedium
                                        )
                                        Text(
                                            text = "Hello head line Small",
                                            modifier = modifier,
                                            style = MaterialTheme.typography.headlineSmall
                                        )
                                        Text(
                                            text = "Hello title Medium",
                                            modifier = modifier,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = "Hello label Small",
                                            modifier = modifier,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                        Text(
                                            text = "Hello body Small",
                                            modifier = modifier,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                    */

                var codeOTP by remember { mutableStateOf("") }

                CodeTextFields(onDoneKeyboardAction = {
                    Toast.makeText(ctx.findActivity(), "Done $codeOTP", Toast.LENGTH_SHORT).show()
                }) {
                    codeOTP = it
                }

                var textValue by remember { mutableStateOf(TextFieldValue("")) }

                CompositionLocalProvider(
                    LocalTextToolbar provides EmptyTextToolbar
                ) {
                    OutlinedTextField(
                        label = { Text("No Paste Input") },
                        value = textValue,
                        onValueChange = { newValue ->
                            textValue = if (newValue.selection.length > 0) {
                                newValue.copy(selection = textValue.selection)
                            } else {
                                newValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        )
                    )
                }

                var text by remember { mutableStateOf("") }
                OutlinedTextField(
                    label = { Text("CurrencyAmountInput") },
                    value = text,
                    onValueChange = {
                        text = if (it.startsWith("0")) {
                            ""
                        } else {
                            it
                        }
                    },
                    visualTransformation = CurrencyAmountInputVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    )
                )

                val errorMessage by viewModel.errors.collectAsState()
                var passwordVisible by rememberSaveable { mutableStateOf(false) }

                KapitalTextField(modifier = Modifier.kapitalCommonHorizontalPadding(),
                    text = viewModel.nameState.valueState.collectAsState().value ?: "",
                    onChange = viewModel.nameState::update,
                    isError = errorName.hasErrors,
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    messageError = (errorName as? PersonalInfoErrors.NameError)?.let {
                        when {
                            it.empty -> stringResource(id = R.string.required_field)
                            else -> null
                        }
                    },

                    //value = password,
                    //onValueChange = { password = it },

                    label = "Password",
                    placeholder = "Password",
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next,
                    ),
                    trailingIcon = {
                        val image = painterResource(id = getIconPassword(passwordVisible))
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(painter = image, description)
                        }
                    })

                var autoSizeText by remember { mutableStateOf("") }
                AutoSizeTextField(inputValue = autoSizeText) {
                    autoSizeText = it
                }

                KapitalPhoneTextField(text = phoneState,
                    onChange = viewModel::updateCapturedPhone,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.double_large_padding))
                        .padding(horizontal = dimensionResource(id = R.dimen.large_padding)),
                    onDoneKeyboardAction = {
                        //keyboardController?.hide()
                    },
                    isError = errorMessage.let {
                        it is LoginError.InvalidPhoneError || it is LoginError.InvalidPhoneLengthPhoneCaptured || it is LoginError.EmptyPhoneCaptured
                    },
                    messageError = getErrorMessage(errorMessage)?.let { stringResource(id = it) })

                KapitalTextField(label = "Full Name",
                    placeholder = "Write Full Name",
                    modifier = Modifier.kapitalCommonHorizontalPadding(),
                    text = viewModel.nameState.valueState.collectAsState().value ?: "",
                    onChange = viewModel.nameState::update,
                    isError = errorName.hasErrors,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    messageError = (errorName as? PersonalInfoErrors.NameError)?.let {
                        when {
                            it.empty -> stringResource(id = R.string.required_field)
                            else -> null
                        }
                    })
                KapitalButton(
                    label = stringResource(id = R.string.app_name).uppercase(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .kapitalCommonTopPadding()
                        .kapitalCommonHorizontalPadding()
                ) {
                    onShowDialog.value = true
                }

                KapitalOutlinedButton(
                    label = stringResource(id = R.string.text_alert_biometrics).uppercase(),
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .kapitalCommonTopPadding()
                        .kapitalCommonHorizontalPadding()
                ) {
                    showDialog.value = true
                }

                Image(
                    painter = painterResource(getIconPassword(successBiometrics.value)),
                    colorFilter = getSimpleColorIcons(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = modifier
                        .fillMaxWidth(0.4f)
                        .wrapContentHeight()
                        .padding(top = dimensionResource(id = R.dimen.large_padding))
                )

                LinkText(
                    linkTextData = listOf(
                        LinkTextData(
                            text = stringResource(id = R.string.faq_extra_question_1)
                        ), LinkTextData(text = mail,
                            tag = "correo Kapital",
                            annotation = "mailto:$mail",
                            onClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                                        val mailto = "mailto: $mail"
                                        data = Uri.parse(mailto)
                                    }
                                    ctx.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    print(e.toString())
                                }
                            }), LinkTextData(
                            text = stringResource(id = R.string.faq_extra_question_2)
                        ), LinkTextData(text = " (55) 7923 2186 ",
                            tag = stringResource(id = R.string.tag_whatsapp),
                            annotation = "https://api.whatsapp.com/send?phone=+525579232186",
                            onClick = {
                                val url = "https://api.whatsapp.com/send?phone=+525579232186"
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse(url)
                                ctx.startActivity(i)
                            }), LinkTextData(
                            text = stringResource(id = R.string.faq_extra_question_3)
                        )
                    ), modifier = Modifier.padding(
                        all = 16.dp
                    )
                )

                if (showDialog.value) {
                    val context = LocalContext.current
                    val activity = context.findActivity()

                    initBiometric(context, activity!!) { result, error ->
                        when (result) {
                            BiometricsResultType.SUCCESS -> {
                                Toast.makeText(
                                    ctx,
                                    "Acciones a realizar en caso de éxito",
                                    Toast.LENGTH_SHORT
                                ).show()
                                showDialog.value = false
                                successBiometrics.value = true
                            }

                            BiometricsResultType.ERROR -> {
                                Toast.makeText(
                                    ctx,
                                    "Acciones a realizar en caso de error $error",
                                    Toast.LENGTH_SHORT
                                ).show()
                                showDialog.value = false
                                successBiometrics.value = false
                            }

                            else -> {
                                Toast.makeText(
                                    ctx, "Cancelado por el usuario", Toast.LENGTH_SHORT
                                ).show()
                                showDialog.value = false
                                successBiometrics.value = false
                            }
                        }
                    }
                }
                if (onShowDialog.value) {
                    KapitalConfirmationDialog(confirmTextButton = stringResource(id = R.string.text_alert_accept),
                        title = stringResource(id = R.string.text_alert_title_logout),
                        confirmButton = {
                            onShowDialog.value = false
                        })
                }
                if (onShowSecondDialog.value) {
                    KapitalDialog(
                        confirmButton = {
                            onShowDialog.value = false
                        },
                        confirmTextButton = stringResource(id = R.string.text_alert_yes),
                        dismissButton = {
                            onShowDialog.value = false
                        },
                        dismissTextButton = stringResource(id = R.string.text_alert_back),
                        title = stringResource(id = R.string.text_alert_title)
                    )
                }

            }
        })
    }

}

@Composable
fun ChartDesign() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {//BarWithLineChart()
        BarWithLineChartAndBackground()
    }
}

@Composable
fun BarWithLineChartAndBackground() {
    val maxRange = 100
    val groupBarData = DataUtils.getGroupBarChartData(50, 100, 3)
    val yStepSize = 10
    val xAxisData =
        AxisData.Builder().axisStepSize(30.dp).bottomPadding(5.dp).backgroundColor(RegentGray)
            .labelData { index -> index.toString() }.build()
    val yAxisData = AxisData.Builder().steps(yStepSize).backgroundColor(RegentGray)
        .labelAndAxisLinePadding(20.dp).axisOffset(20.dp)
        .labelData { index -> (index * (maxRange / yStepSize)).toString() }.build()
    val linePlotData = LinePlotData(
        lines = listOf(
            Line(
                DataUtils.getLineChartData(50, maxRange = 100),
                lineStyle = LineStyle(color = Color.Blue),
                intersectionPoint = IntersectionPoint(),
                selectionHighlightPoint = SelectionHighlightPoint(),
                selectionHighlightPopUp = SelectionHighlightPopUp()
            ), Line(
                DataUtils.getLineChartData(50, maxRange = 100),
                lineStyle = LineStyle(color = Color.Black),
                intersectionPoint = IntersectionPoint(),
                selectionHighlightPoint = SelectionHighlightPoint(),
                selectionHighlightPopUp = SelectionHighlightPopUp()
            )
        )
    )
    val colorPaletteList = DataUtils.getColorPaletteList(3)
    val legendsConfig = LegendsConfig(
        legendLabelList = DataUtils.getLegendsLabelData(colorPaletteList), gridColumnCount = 3
    )
    val barPlotData = BarPlotData(
        groupBarList = groupBarData,
        barStyle = BarStyle(barWidth = 35.dp),
        barColorPaletteList = colorPaletteList
    )
    val combinedChartData = CombinedChartData(
        combinedPlotDataList = listOf(barPlotData, linePlotData),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = FunBlue50
    )
    Column(
        Modifier.height(500.dp)
    ) {
        CombinedChart(
            modifier = Modifier.height(400.dp), combinedChartData = combinedChartData
        )
        Legends(
            legendsConfig = legendsConfig
        )
    }
}

@Composable
fun BarWithLineChart() {
    val maxRange = 100
    val groupBarData = DataUtils.getGroupBarChartData(50, 100, 3)
    val yStepSize = 10

    val xAxisData = AxisData.Builder().axisStepSize(30.dp).bottomPadding(5.dp)
        .labelData { index -> index.toString() }.build()

    val yAxisData =
        AxisData.Builder().steps(yStepSize).labelAndAxisLinePadding(20.dp).axisOffset(20.dp)
            .labelData { index -> (index * (maxRange / yStepSize)).toString() }.build()

    val linePlotData = LinePlotData(
        lines = listOf(
            Line(
                DataUtils.getLineChartData(50, maxRange = 100),
                lineStyle = LineStyle(color = Color.Blue),
                intersectionPoint = IntersectionPoint(),
                selectionHighlightPoint = SelectionHighlightPoint(),
                selectionHighlightPopUp = SelectionHighlightPopUp()
            ), Line(
                DataUtils.getLineChartData(50, maxRange = 100),
                lineStyle = LineStyle(color = Color.Black),
                intersectionPoint = IntersectionPoint(),
                selectionHighlightPoint = SelectionHighlightPoint(),
                selectionHighlightPopUp = SelectionHighlightPopUp()
            )
        )
    )

    val colorPaletteList = DataUtils.getColorPaletteList(3)
    val legendsConfig = LegendsConfig(
        legendLabelList = DataUtils.getLegendsLabelData(colorPaletteList), gridColumnCount = 3
    )
    val barPlotData = BarPlotData(
        groupBarList = groupBarData,
        barStyle = BarStyle(barWidth = 35.dp),
        barColorPaletteList = colorPaletteList
    )
    val combinedChartData = CombinedChartData(
        combinedPlotDataList = listOf(barPlotData, linePlotData),
        xAxisData = xAxisData,
        yAxisData = yAxisData
    )
    Column(
        Modifier.height(500.dp)
    ) {
        CombinedChart(
            modifier = Modifier.height(400.dp), combinedChartData = combinedChartData
        )
        Legends(
            legendsConfig = legendsConfig
        )
    }
}

fun getErrorMessage(error: LoginError): Int? {
    return when (error) {
        LoginError.EmptyPhoneCaptured -> R.string.required_field
        LoginError.InvalidPhoneLengthPhoneCaptured -> R.string.invalid_phone_lenght
        else -> null
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("FunctionNaming")
fun GreetingPreview() {
    KapitalTheme {
        Greeting("Android")
    }
}
