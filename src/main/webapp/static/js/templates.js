/**
 * Created by rodion on 27.02.2016.
 */


function loadAndInitTemplates() {
    $.ajax(DUST_CREATE_CHANGE, {
        method: 'GET',
        success: function (data) {
            dust.loadSource(dust.compile(data, "createChange"));
        }
    });
    $.ajax(DUST_CHANGES_TEMPLATE_PATH, {
        method: 'GET',
        success: function (data) {
          dust.loadSource(dust.compile(data, "changesTemplate"));
        }
    });
    $.ajax(DUST_CURRENCY_INPUT_TEMPLATE_PATH, {
        method: 'GET',
        success: function (data) {
            dust.loadSource(dust.compile(data, "currencyInputTemplate"));
        }
    });
};

$(function () {
    loadAndInitTemplates();
});
