<div class="modal-content">
    <h4>Create new {#expense} expense {:else} income {/expense} </h4>

    <div class="row">
        <form class="col s12" id="create-change-form">
            <div class="row">
                <input id="new-change-input-name" class="input-field col s12" type="text" name="name"/>
                <label for="new-change-input-name">What was the {#expense} expense {:else} income {/expense} ?</label>
            </div>
            <div class="row">
                <input class="input-field col s12" id="new-expense-input-value" type="number" name="amount"/>
                <label for="new-change-input-value">How much did you {#expense} spend {:else} get {/expense} ?</label>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <select id="new-change-input-currency" name="currency">
                        <option class="currency-input" data-currency="UAH" id="currency-UAH" value="UAH">UAH
                        </option>
                        <option class="currency-input" data-currency="USD" id="currency-USD" value="USD">USD
                        </option>
                        <option class="currency-input" data-currency="EUR" id="currency-eur" value="EUR">EUR
                        </option>
                        <option class="currency-input" data-currency="RUB" id="currency-rub" value="RUB">RUB
                        </option>
                    </select>
                </div>
                <label for="new-change-input-currency">In what currency ?</label>

            </div>
            <div class="row">
                <input class="input-field col s12 datepicker" id="new-change-input-date" type="date" name="date"/>
                <label for="new-change-input-date">When did you {#expense} spend {:else} get money {/expense} ? ( Or
                    planning to ) </label>
            </div>

            <div class="row">
                <input type="checkbox" name="periodic" id="new-change-input-periodic"/>
                <label for="new-change-input-periodic"
                       onclick="$('#change-rule-input-div').toggleClass('hidden')">Periodic change?</label>
            </div>
            <div class="row hidden" id="change-rule-input-div">
                <input class="typeahead input-filed col s12" id="new-change-input-rule" type="text" name="rule"/>
                <label for="new-change-input-rule">What's the rule ?</label>
            </div>
        </form>
    </div>
</div>
<div class="modal-footer change-button">
    <a href="#!" class=" modal-action modal-close waves-effect waves-green btn-flat"
       onclick="{#expense}createExpense(){:else}createIncome(){/expense}">Create</a>
    <a href="#!" class=" modal-action modal-close waves-effect waves-green btn-flat" onclick="">
        {#expense} I didn't spend this much! {:else} I didn't get this :( {/expense}
    </a>
</div>
