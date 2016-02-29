/**
 * Created by rodion on 27.02.2016.
 */


function loadAndInitTemplates() {
    $.ajax(DUST_CHANGES_TEMPLATE_PATH, {
        method: 'GET',
        success: function (data) {
          dust.loadSource(dust.compile(data, "changesTemplate"));
        }
    });
};

$(function () {
    loadAndInitTemplates();
});
