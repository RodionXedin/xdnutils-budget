/**
 * Created by rodion on 24.02.2016.
 */
function createExpense() {
    var walletSelector = "#create-expense-form";
    var currentWallet = {wallet: $(walletSelector).parents('div.modal').data(WALLET_NAME_DATA_ATTR)};
    $.ajax("/expense-create", {
        method: 'POST',
        data: extractValuesFromForm(walletSelector, currentWallet),
        success: function (data) {
            Materialize.toast('Expense created', 4000);
            $(walletSelector + ' input,select').each(function (index, value) {
                $(value).val('');
            });
        }
    });
};

function initExpenseScripts() {

};

$(function () {
    initExpenseScripts();
});