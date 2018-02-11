function showToast(headingText, textToShow, iconToDisplay) {
    $.toast({
        heading: headingText,
        text: textToShow,
        showHideTransition: 'slide',
        icon: iconToDisplay,
        loader: false
    });
}
