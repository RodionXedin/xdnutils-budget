/**
 * Created by rodion on 24.02.2016.
 */
function createExpense() {
    var walletSelector = "#create-change-form";
    var currentWallet = {wallet: $(walletSelector).parents('div.modal').data(WALLET_NAME_DATA_ATTR)};
    submitForm('/create-expense', walletSelector, currentWallet, true, 'Expense Created', reloadChangesTables);
};

function initExpenseScripts() {

};

$(function () {
    initExpenseScripts();
});