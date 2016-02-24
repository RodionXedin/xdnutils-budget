$.getScript("static/js/util.js");

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


function createExpense() {
    var walletSelector = "#create-expense-form";
    $.ajax("/expense-create", {
        method: 'POST',
        data: extractValuesFromForm(walletSelector),
        success: function (data) {
            Materialize.toast('Expense created', 4000);
        }
    })
    ;
}

function openWallet(walletName) {
    $.ajax("/get-wallet", {
        method: 'GET',
        data: {
            name: walletName
        },
        success: function (data) {
            populateWalletModal(data.wallet);
            $("#modal-wallet").openModal();
            createCharts();

        }
    });
}

function populate_user_info_div(data) {
    $("#user-info-div-name").text('User : ' + data.userName);
    $('#user-info-div-wallet-count-badge').text(data.wallets.length + ' wallets');
    if (!data.newUser) {
        Materialize.toast('Welcome back ' + data.userName, 4000);
    }
    else {
        Materialize.toast('Hey there! ' + data.userName + ' registered', 4000)
    }

}

function populateWalletModal(walletData) {
    $("#modal-wallet-name").text(walletData.name);

    return this;
}

function addWalletToWalletList(walletShortenedName, walletName) {
    var wallet_row = $(document.createElement("li"));
    wallet_row.addClass("collection-item");
    wallet_row.html('<div>' + walletShortenedName + '<a onclick="openWallet(\'' + walletName + '\')" href="#!" class="secondary-content"><i class="material-icons">send</i></a></div>');
    $("#user-info-wallet-collection li:last").after(wallet_row);
}


(function ($) {

    $(function () {
        var create_wallet_button = $("#create-wallet-send-button").get(0);
        create_wallet_button.addEventListener("click", function () {
            $.ajax('/create-wallet', {
                method: 'GET',
                data: {
                    name: $("#create-wallet-name").val()
                },
                success: function (data) {
                    addWalletToWalletList(data.wallet.shortenedName, data.wallet.name);
                    var wallet_modal = $("#modal-wallet");
                    populateWalletModal(data.wallet)
                    $("#modal-create-new-wallet").closeModal();
                    wallet_modal.openModal();
                    createCharts();
                }
            })
        })
    });


    $(function () {

        $('#user-info-wallet-add-wallet-button').leanModal();
    });


    $(function () {
        $.ajax("/login").success(function (data) {
            if (data.result == "success") {
                $("#user-info-div").toggle('slide');
                populate_user_info_div(data);
                populateUserInfoWallets();
            } else {
                $("#sign-in-name-input-name-div").toggle('slide');
            }

        })
    });

    $(function () {
        var sign_in_form_input_button_back = $("#sign-in-name-input-password-button-back").get(0);
        sign_in_form_input_button_back.addEventListener('click', function () {
            $("#sign-in-name-input-password-div").toggle({
                effect: 'slide', complete: function () {
                    $("#sign-in-name-input-name-div").toggle('slide');
                }
            })
        })
    });

    $(function () {
        var sign_in_form_input_button_name = $("#sign-in-name-input-name-button-next").get(0);
        sign_in_form_input_button_name.addEventListener('click', function () {
            $("#sign-in-name-input-name-div").toggle({
                effect: 'slide', complete: function () {
                    $("#sign-in-name-input-password-div").toggle('slide');
                }
            })
        })
    });


    $(function () {
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
    });


    $(function () {

        $('.button-collapse').sideNav();
        $('.parallax').parallax();

    }); // end of document ready
})(jQuery); // end of jQuery name space

function createCharts() {
    $('#charts-container').highcharts({
        title: {
            text: 'Monthly Average Temperature',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: WorldClimate.com',
            x: -20
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Temperature (°C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'New York',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: 'Berlin',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }, {
            name: 'London',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }]
    });
};