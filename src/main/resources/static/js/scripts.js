document.addEventListener('DOMContentLoaded', function() {
    // Validação de Formulário de Registro
    const registrarForm = document.querySelector('form[action="/usuarios/registrar"]');
    if (registrarForm) {
        registrarForm.addEventListener('submit', function(event) {
            const senha = document.querySelector('input[name="senha"]').value;
            const senhaError = document.getElementById('senhaError');
            if (senha.length < 6) {
                senhaError.textContent = 'A senha deve ter pelo menos 6 caracteres.';
                event.preventDefault();
            } else {
                senhaError.textContent = '';
            }
        });
    }

    // Validação de Formulário de Login
    const loginForm = document.querySelector('form[action="/usuarios/login"]');
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            const nomeUsuario = document.querySelector('input[name="nomeUsuario"]').value;
            const senha = document.querySelector('input[name="senha"]').value;
            if (nomeUsuario.trim() === '' || senha.trim() === '') {
                alert('Por favor, preencha todos os campos.');
                event.preventDefault();
            }
        });
    }

    // Feedback ao usuário ao criar uma nova vaga
    const vagaForm = document.querySelector('form[action="/vagas"]');
    if (vagaForm) {
        vagaForm.addEventListener('submit', function(event) {
            const vagaSuccess = document.getElementById('vagaSuccess');
            vagaSuccess.textContent = 'Vaga criada com sucesso!';
            vagaSuccess.style.display = 'block';
            setTimeout(() => vagaSuccess.style.display = 'none', 5000); // Oculta após 5 segundos
        });
    }
});

