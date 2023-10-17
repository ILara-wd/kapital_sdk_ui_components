package com.kapital.kapitalApp.navigation

object Navigation {
    const val root = "root"

    object Login {
        const val home = "login"
        const val codeValidation = "login_code"
        const val fromCheckoutParameter = "fromCheckout"
    }

    object Authenticated {
        const val route = "authenticated"
    }

    object Home {
        const val home = "home"
    }

    object Order {
        const val order = "order"
        const val orderId = "idOrder"
        const val orderIdStateParam = "idOrderStateParam"

        object Links {
            const val route = "$order-links-payment-modal"
            const val linkOxxoParam = "linkOxxo"
            const val linkSpeiParam = "linkSpei"
            const val routerWithParams =
                "$route?$linkOxxoParam={$linkOxxoParam}&$linkSpeiParam={$linkSpeiParam}"
        }
    }

    object Files {
        const val pdf = "PDF"
        const val pdf_terms = "pdf_terms"
        const val pdf_privacy = "pdf_privacy"
    }

    object Profile {
        const val profile = "profile"
        const val personal_information = "personal_information"
        const val faq = "FAQ"
        const val support = "support"
        const val doesWork = "does_work"
        const val toClose = "toClose"
        const val profileWithParameters = "$profile?$toClose={$toClose}"
    }

    object Onboarding {
        const val route = "onboarding"
        const val home = "onboarding-view"
        const val tutorial = "tutorial-view"
    }

    object SignUp {
        const val signup = "signup"
        const val login = "signup-login"
        const val personalInfo = "personalInfo"
        const val addressInfo = "addressInfo"
        const val codeValidation = "second_code"
        const val approved = "approved"
        const val WaitingList = "waitingList"
        const val customerValidation = "openCustomerVerification"
    }

    object payment {
        const val next_payment = "next-payment"
        const val whole_payment = "whole-payment"
        const val success_payment = "success-payment"

        const val payment_method = "payment-method"
        const val addCreditCard = "payment-add-credit-card"
        const val addCreditCardAfterSignup = "payment-add-credit-card-after-signup"
        const val addCreditCardBeforeLoan = "payment-add-credit-card-before-loan"
        const val purchaseCamera = "payment-purchase-camera"
        const val success = "payment-charge-success"
        const val failure = "payment-charge-failure"

        object CvvDynamicModule {
            const val route = "payment-cvv-dynamic-module"
            const val customerIdParam = "customerIdParam"
            const val tokenLoan = "tokenLoanParam"
            const val isAssignedNextQuinena = "isAssignedNextQuincenaParam"
            const val isOnline = "isOnlineParam"
            const val paymentType = "paymentTypeParam"
            const val amountParam = "amountParam"
            const val orderIdParam = "orderIdParam"
            const val transactionIdParam = "transactionIdParam"
            const val installmentPlanIdParam = "installmentPlanIdParam"

            const val routeWitParameters = route +
                    "?$customerIdParam={$customerIdParam}&" +
                    "$tokenLoan={$tokenLoan}&" +
                    "$isAssignedNextQuinena={$isAssignedNextQuinena}&" +
                    "$isOnline={$isOnline}&" +
                    "$paymentType={$paymentType}&" +
                    "$amountParam={$amountParam}&" +
                    "$orderIdParam={$orderIdParam}&" +
                    "$transactionIdParam={$transactionIdParam}&" +
                    "$installmentPlanIdParam={$installmentPlanIdParam}"
        }

        object purchaseDetail {
            const val name = "payment-purchase-detail"
            const val parameter = "purchaseLink"
            const val idInstallmentPlan = "idInstallmentPlan"
            const val invalidPlan = "-1"
        }

        object PurchaseDetailProxy {
            const val name = "payment-purchase-detail-proxy"
        }

        const val checkoutDetailNotLogin = "payment-checkout-detail-not-logIn"

        object InstallmentsPlan {
            const val route = "payment-installments-plan"
            const val idLoan = "idLoanParam"
            const val routeWithParams = "$route/{$idLoan}"
        }
    }

    object Stores {
        const val home = "stores-show-view"
        const val route = "stores"

        object InStore {
            const val home = "InStoreCanBuy"

            object Physical {
                const val home = "InStoreCanBuyPhysical"
                const val parameter = "canBuy"
                val routeWitParameter
                    get() = "$home?$parameter={$parameter}"
            }
        }

        object ListLocation {
            const val home = "InStoreCanBuyListStoresLocation"
            const val parameter = "idStore"
            const val routeWithParameters = "$home/{$parameter}"
            const val routeWithParametersFromHome = "home/$home/{$parameter}"
        }
    }

    object Image {
        const val zoom = "zoom"
    }

    object webView {
        const val dashboard = "dashboard"
        const val dashboardWithUrlSelect = "$dashboard?urlSelect={urlSelect}"
        const val dashboardWithUrlSelectFromOrder =
            "${Order.order}-$dashboard?urlSelect={urlSelect}"
        const val dashboardWithUrlSelectFromProfile =
            "${Profile.profile}-$dashboard?urlSelect={urlSelect}"
        const val stores = "stores"
    }

    object Errors {
        const val root = "mx/aplazo/mobile/app/errors"

        const val previousParameter = "previousRouteParameter"
        const val isOnlineParameter = "isOnlineParameter"

        object DefaultedCustomer {
            const val route = "$root-defaultedCustomer"
            const val parameter = "messageSupport"
            const val routeWithParameter = "$route?$parameter={$parameter}"
        }

        const val order = "$root-order"
        const val genericError = "$root-genericError"
        const val internetError = "$root-internetError"
        const val notOrderFound = "$root-notOrderFound"
        const val invalidMerchantConfiguration = "$root-invalidMerchantConfiguration"

        object UnAffordable {
            const val route = "$root-unaffordable-order"
            const val loanTotal = "LoanTotalparameter"
            const val customerLimit = "customerLimit"
            const val routeWitParameters =
                "$route?$loanTotal={$loanTotal}&$customerLimit={$customerLimit}"
        }

        object Router {
            const val route: String = "$root-router"
            const val parameter = "numberError"
            const val routeWithParameters =
                "$route?$parameter={$parameter}&$previousParameter={$previousParameter}&$isOnlineParameter={$isOnlineParameter}"
        }
    }

    object ApplicationNotification {
        private const val root = "notification"
        const val update = "$root-updateApplication"
        const val updatable = "$root-updatableApplication"
    }
}
