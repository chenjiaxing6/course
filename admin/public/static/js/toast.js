Toast = {
    success: function (message) {
        Swal.fire({
            position: 'botton-end',
            icon: 'success',
            title: message,
            showConfirmButton: false,
            timer: 1000
        })
    },

    error: function (message) {
        Swal.fire({
            position: 'botton-end',
            icon: 'error',
            title: message,
            showConfirmButton: false,
            timer: 1000
        })
    },

    warning: function (message) {
        Swal.fire({
            position: 'botton-end',
            icon: 'warning',
            title: message,
            showConfirmButton: false,
            timer: 1000
        })
    }
};