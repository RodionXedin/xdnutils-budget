/**
 * Created by rodion on 24.02.2016.
 */
function populate_user_info_div(data) {
    $("#user-info-div-name").text('User : ' + data.userName);
    $('#user-info-div-wallet-count-badge').text(data.wallets.length + ' wallets');
    if (!data.newUser) {
        Materialize.toast('Welcome back ' + data.userName, 4000);
    }
    else {
        Materialize.toast('Hey there! ' + data.userName + ' registered', 4000)
    }

};

function populateUserInfoWallets() {
    $.ajax("/get-user-wallets", {
        method: 'GET',
        success: function (data) {
            $.each(data.wallets, function (key, value) {
                addWalletToWalletList(value.shortenedName, value.name);
            })
        }
    })
};

function initLoginButton() {
    $.ajax("/login").success(function (data) {
        if (data.result == "success") {
            $("#user-info-div").toggle('slide');
            populate_user_info_div(data);
            populateUserInfoWallets();
        } else {
            $("#sign-in-name-input-name-div").toggle('slide');
        }

    })
};


function initPasswordBackButton() {
    var sign_in_form_input_button_back = $("#sign-in-name-input-password-button-back").get(0);
    sign_in_form_input_button_back.addEventListener('click', function () {
        $("#sign-in-name-input-password-div").toggle({
            effect: 'slide', complete: function () {
                $("#sign-in-name-input-name-div").toggle('slide');
            }
        })
    })
};

function initUserSignNameButton() {
    var sign_in_form_input_button_name = $("#sign-in-name-input-name-button-next").get(0);
    sign_in_form_input_button_name.addEventListener('click', function () {
        $("#sign-in-name-input-name-div").toggle({
            effect: 'slide', complete: function () {
                $("#sign-in-name-input-password-div").toggle('slide');
            }
        })
    })
};

function initLogin() {
    var sign_in_form_input_button_name = $("#sign-in-name-input-password-button-next").get(0);
    sign_in_form_input_button_name.addEventListener('click', function () {

        $.ajax("/login", {
            method: 'POST',
            data: {
                name: $("#sign-in-name-input-name").val(),
                password: $("#sign-in-name-input-password").val()
            },
            success: function (data) {
                populate_user_info_div(data);
                $("#sign-in-name-input-password-div").toggle({
                    effect: 'slide', complete: function () {
                        $("#user-info-div").toggle('slide');
                        populateUserInfoWallets();
                    }
                })
            }
        })
    })
};


function initUserScripts() {
    initLoginButton();
    initPasswordBackButton();
    initUserSignNameButton();
    initLogin();
};

$(function () {
    initUserScripts();
});