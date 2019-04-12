$(function () {
    var parent = $(".embaralha");
    var forms = parent.children();
    while (forms.length) {
        parent.append(forms.splice(Math.floor(Math.random() * forms.length), 1)[0]);
    }
});