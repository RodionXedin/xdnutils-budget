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

function openWallet(walletName) {
    $.ajax("/get-wallet", {
        method: 'GET',
        data: {
            name: walletName
        },
        success: function (data) {
            populateWalletModal(data.wallet);
            $("#modal-wallet").openModal();

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