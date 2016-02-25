/**
 * Created by rodion on 24.02.2016.
 */
function extractValuesFromForm(formSelector, additionalAttributes) {
    var form = $(formSelector);
    var result = {};

    $("#create-expense-form input,select").each(function (index, value) {
        result[value.name] = $(value).val();
    });

    $.extend(result, additionalAttributes);

    return result;
}