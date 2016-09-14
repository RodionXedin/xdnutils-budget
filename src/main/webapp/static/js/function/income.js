/**
 * Created by rodion on 26.02.2016.
 */
function createIncome() {
    var walletSelector = "#create-change-form";
    var currentWallet = {wallet: $(walletSelector).parents('div.modal').data(WALLET_NAME_DATA_ATTR)};
    submitForm('/create-income',walletSelector, currentWallet, true, 'Income Created',reloadChangesTables);

};

function initIncomeScripts() {
    $('.datepicker').pickadate({
        format: 'yyyy-mm-dd',
        formatSubmit: 'yyyy-mm-dd',
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15 // Creates a dropdown of 15 years to control year
    });
};

$(function () {
    initIncomeScripts();
});