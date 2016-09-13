/**
 * Created by rodion on 24.02.2016.
 */
function openCreateExpenseModal(event) {
    $('#modal-create-expense').openModal();
    $('#modal-create-expense').data(WALLET_NAME_DATA_ATTR, $(event.target).parents('div.modal').data(WALLET_NAME_DATA_ATTR));
};

function openCreateIncomeModal(event) {
    $('#modal-create-income').openModal();
    $('#modal-create-income').data(WALLET_NAME_DATA_ATTR, $(event.target).parents('div.modal').data(WALLET_NAME_DATA_ATTR));
};

function getPredictions(id) {
    $.ajax("/predictions", {
            method: 'GET',
            data: {
                currentValue: $(id).val()
            },
            success: function (data) {
                allPossibleWords = data.words;
                $('.typeahead').typeahead({
                        minLength: 1,
                        highlight: true
                    },
                    {
                        name: 'mySource',
                        source: substringMatcher(allPossibleWords)
                    });
            }
        }
    )
    ;
}

function deleteChange(key) {
    $.ajax('/delete-change',
        {
            method: 'GET',
            data: {
                key: key,
                walletName: $("#modal-wallet").data(WALLET_NAME_DATA_ATTR)
            },
            success: function (data) {
                reloadChangesTables();
                Materialize.toast("Change Deleted", 4000);
            }
        });
};

function populateRatesTable(rates) {
    $("#modal-wallet").data("rates", rates);
    var defaultCurrency = $('#user-info-div').data("defaultCurrency");
    $(".currency-input").each(function (i, v) {
        if (rates[$(v).data("currency") + defaultCurrency] != undefined) {
            dust.render('currencyInputTemplate',
                {currency: $(v).data("currency"), rate: rates[$(v).data("currency") + defaultCurrency]},
                function (err, out) {
                    $(v).html(out);
                });
        }
        console.log()
    })
};

function populateWalletModal(walletData) {
    $("#modal-wallet-name").text(walletData.name);
    $("#modal-wallet").data(WALLET_NAME_DATA_ATTR, walletData.name);
    return this;
};

function openWallet(walletName) {
    $.ajax("/get-wallet", {
        method: 'GET',
        data: {
            name: walletName
        },
        success: function (data) {
            populateRatesTable(data.rates);
            populateWalletModal(data.wallet);
            $("#modal-wallet").openModal();
            reloadChangesTables();
        }
    });

};

function loadChart() {
    var walletName = $("#modal-wallet").data(WALLET_NAME_DATA_ATTR);
    $.ajax("/get-general-chart", {
        method: 'GET',
        data: {
            name: walletName
        },
        success: function (data) {
            $('#charts-container').highcharts(data.chart);
        }
    });
};

function reloadChangesTables() {
    getChanges(CHANGES_ALL_TABLE, 'all');
    getChanges(CHANGES_INCOME_TABLE, 'income');
    getChanges(CHANGES_OUTCOME_TABLE, 'outcome');
    loadChart();
}

function getChanges(destinationNode, type) {
    var walletName = $("#modal-wallet").data(WALLET_NAME_DATA_ATTR);
    $.ajax('/get-changes', {
        method: 'GET',
        data: {
            walletName: walletName,
            type: type
        },
        success: function (data) {
            dust.render('changesTemplate', data, function (err, out) {
                $(destinationNode).html(out);
            });
            console.log('changes for type : ' + type + 'loaded');
        }
    });


}
function displayChangeTables(state) {
    $(CHANGES_ALL_TABLE).toggleClass('hidden');
    $(CHANGES_INCOME_TABLE).toggleClass('hidden');
    $(CHANGES_OUTCOME_TABLE).toggleClass('hidden');
}

function addWalletToWalletList(walletShortenedName, walletName) {
    var wallet_row = $(document.createElement("li"));
    wallet_row.addClass("collection-item");
    wallet_row.html('<div>' + walletShortenedName + '<a onclick="openWallet(\'' + walletName + '\')" href="#!" class="secondary-content"><i class="material-icons">send</i></a></div>');
    $("#user-info-wallet-collection li:last").after(wallet_row);
};

function initCreateWalletButton() {
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
};

var allPossibleWords = ["each", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "month", "week"];


var substringMatcher = function (strs) {
    return function findMatches(q, cb) {
        var matches, substringRegex;

        // an array that will be populated with substring matches
        matches = [];

        //get last word to match
        var words = q.split(' ');
        var currentWord = words[words.length - 1];
        words.splice(words.length - 1);
        var currentString = words.join(" ");

        // regex used to determine if a string contains the substring `q`
        substrRegex = new RegExp(currentWord, 'i');

        // iterate through the pool of strings and for any string that
        // contains the substring `q`, add it to the `matches` array
        $.each(strs, function (i, str) {
            if (substrRegex.test(str)) {
                matches.push(currentString + " " + str);
            }
        });

        cb(matches);
    };
};

function initAddWalletButton() {
    $('#user-info-wallet-add-wallet-button').leanModal();
};

function initWalletScripts() {
    initCreateWalletButton();
    initAddWalletButton();
    // initTypeAhead();
};

$(function () {
    initWalletScripts();
});