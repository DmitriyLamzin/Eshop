/**
 * Created by Dmitriy on 29.02.2016.
 */
$(document).ready(function()  {
    if (window.history.state == "/admin/category") {
        loadAllCategories();
    }else if (window.history.state == "/admin/subcategory"){
        loadAllSubCategories();
    }else if (window.history.state == "/admin/catalog"){

    }
});