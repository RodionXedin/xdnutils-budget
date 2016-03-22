/**
 * Created by rodion on 24.02.2016.
 */
function extractValuesFromForm(formSelector, additionalAttributes) {
    var form = $(formSelector);
    var result = {};

    $(formSelector + ">div" + ">div>select," + formSelector + ">div" + ">input").each(function (index, value) {
        result[value.name] = $(value).val();
    });

    $.extend(result, additionalAttributes);

    return result;
};

function submitForm(node, formSelector, additionalData, clearForm, successMessage, successCallback) {
    $.ajax(node, {
        method: 'POST',
        data: extractValuesFromForm(formSelector, additionalData),
        success: function (data) {
            Materialize.toast(successMessage, 4000);
            if (clearForm) {
                $(formSelector + ' input,select').each(function (index, value) {
                    $(value).val('');
                })
            }
            ;
            successCallback();
        }
    });

};

