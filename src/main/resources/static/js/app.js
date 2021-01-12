const moodInput = document.getElementById("mood");
let mood = parseInt(moodInput.value);

const updateButtons = (updateEventListeners) => {
    const buttons = document.getElementsByClassName("btn-mood");
    for(let i = 0; i < buttons.length; i++) {

        console.log("AAAAAAAAAAAAAAA", mood, i+1);
        buttons[i].className = `btn btn-${mood == i+1 ? 'success' : 'primary'} btn-fab btn-round btn-mood`;

        if(updateEventListeners) {
            buttons[i].addEventListener('click', () => {
                mood = i+1;
                moodInput.value = mood;
                updateButtons(false);
            })
        }
    }
}

if(moodInput) {
    updateButtons(true);
}