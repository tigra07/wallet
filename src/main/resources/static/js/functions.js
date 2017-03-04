function toggleCategories() {
    $('#sessionType').change(function () {
        <!-- скрываем / отображаем доп параметры -->
        if ($('#sessionType').val() == "AUTO"){
            $('.appiumVars').show(400);
        } else {
            $('.appiumVars').hide(400);
            $('#deviceId').val("");
            $('#platformVersion').val("");
            $('#app').val("");
            $('#udid').val("");
        }

        <!-- влияем на выбор платформы -->
        var options = "";
        if ($('#sessionType').val() == 'AUTO'){
            options = "<option value='1'>ANDROID</option>"
                + "<option value='4'>IPHONE</option>";
        } else {
            options = "<option value='1'>ANDROID</option>"
                + "<option value='2'>WINPHONE</option>"
                + "<option value='3'>JAVA</option>"
                + "<option value='4'>IPHONE</option>";
        }
        $('#platform').html(options);
    });
}