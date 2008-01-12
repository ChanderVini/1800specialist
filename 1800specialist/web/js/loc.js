var locsize = 0;
var reccuringamt = 0;
var setupfee = 0;

function updateAmount () {
    var amount = setupfee;
    for (var cnt = 0; cnt < locsize; cnt++) {
        var selele = document.getElementsByName ('locationVOs['+cnt+'].selected');
        var statele = document.getElementsByName ('locationVOs['+cnt+'].status');
        if (selele && selele != 'undefined' && selele.length > 0 && statele && statele != 'undefined' && statele.length > 0) {
            if (selele[0].checked && statele[0].value == 'NEW') {
                amount += reccuringamt;
            }
        }
    }
    var amtele = document.getElementsByName ('amount');
    if (amtele && amtele != 'undefined' && amtele.length > 0) {
        amtele[0].value = amount;
    }
}