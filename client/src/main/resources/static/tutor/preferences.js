$(document).ready(() => {
    let names = {}, preferences = [];
    const reloadPreferences = () => {
        $.when(
            $.getJSON(apiUrl + "/staff/names", function (namesJson) {
                names = namesJson;
            }),
            $.getJSON(apiUrl + "/preferences/" + asurite, function (preferencesJson) {
                preferences = preferencesJson.sort((pref1, pref2) => pref1.preferenceNumber - pref2.preferenceNumber);
            })
        ).then(() => {
            const list = $("#preferenceList");
            list.empty();
            preferences.forEach(pref => {
                list.append(
                    $(`<li class="panel panel-default" id="preference${pref.preferenceNumber}" style="list-style: none">
                            <div class="panel-heading scheduled"> 
                                <h4 class="panel-title scheduled"> 
                                    ${pref.preferenceNumber}. ${names[pref.preferredAsurite]}
                                </h4>
                            </div>
                        </li>`)
                )
            })

            $(".sortablePreferences").sortable({
                start: function (event, ui) {
                    // store the initial position of the dragged item
                    ui.item.data('start_pos', ui.item.index());
                },
                update: function (event, ui) {
                    // on finish sort, get the start position
                    const startIndex = ui.item.data('start_pos');
                    // get the final sorted position
                    const endIndex = ui.item.index();

                    // get the ids of the sorted questions
                    const sortedIDs = $(`.sortablePreferences`).sortable("toArray");

                    // store the pref to move
                    let temp = preferences[startIndex];
                    // if moving the pref to a larger index
                    if (startIndex < endIndex) {
                        // shift prefs to left
                        for (let i = startIndex; i < endIndex; i++) {
                            preferences[i] = preferences[i + 1];
                        }
                    } else {
                        // else shift prefs to the right
                        for (let i = startIndex; i > endIndex; i--) {
                            preferences[i] = preferences[i - 1];
                        }
                    }
                    // put the pref in its place
                    preferences[endIndex] = temp;
                }
            })
        })
    }

    let saveButton = $("#savePreferencesButton");
    const fixPreferenceNumbers = () => {
        saveButton.attr("disabled", true);
        saveButton.text("Saving Preferences");
        let data = preferences.map((preference, index) => {
            return Object.assign({}, preference, {preferenceNumber: index + 1})
        })
        $.ajax({
            type: "PUT",
            url: `${apiUrl}/preferences`,
            data: JSON.stringify(data),
            headers: {"Accept": "application/json", "Content-Type": "application/json"},
            success: () => {
                saveButton.removeAttr("disabled");
                saveButton.text("Save Preferences");
                reloadPreferences();
            }
        });
    };

    saveButton.click(fixPreferenceNumbers)

    reloadPreferences();
});