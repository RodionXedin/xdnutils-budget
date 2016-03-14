<table class="striped centered">
    <thead>
    <tr>
        <th data-field="Name">Name</th>
        <th data-field="Amount">Amount</th>
        <th data-field="Currency">Currency</th>
        <th data-field="Date">Date</th>
        <th data-field="Type">Type</th>
        <th data-field="Delete">Delete</th>
    </tr>
    </thead>
    <tbody>
    {#expenses}
        <tr>
            <td>
                {name}
            </td>
            <td>
                {amount}
            </td>
            <td>
                {currency}
            </td>
            <td>
                {date}
            </td>
            <td>
                {type}
            </td>
            <td>
                <a class="waves-effect waves-light btn valign-wrapper" onclick="deleteChange('{key}')"><i class="material-icons">delete</i></a>
            </td>
        </tr>
    {/expenses}
    </tbody>
</table>