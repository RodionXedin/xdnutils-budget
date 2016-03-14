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
            populateWalletModal(data.wallet);
            $("#modal-wallet").openModal();
            reloadChangesTables();
            createCharts();

        }
    });
};

function reloadChangesTables() {
    getChanges(CHANGES_ALL_TABLE, 'all');
    getChanges(CHANGES_INCOME_TABLE, 'income');
    getChanges(CHANGES_OUTCOME_TABLE, 'outcome');
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


function initAddWalletButton() {
    $('#user-info-wallet-add-wallet-button').leanModal();
};

function initWalletScripts() {
    initCreateWalletButton();
    initAddWalletButton();
};

$(function () {
    initWalletScripts();
});