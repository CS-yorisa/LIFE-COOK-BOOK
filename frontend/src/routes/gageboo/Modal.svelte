<script>
    export let visible = false;
    export let onClose = () => {};
</script>

<style>
    .overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100vw;
        height: 100vh;
        background-color: rgba(0, 0, 0, 0.4);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 2000;
    }

    .modal {
        background-color: white;
        padding: 2rem;
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        max-width: 900px;
        width: 90%;
        text-align: center;
    }

    .close-btn {
        margin-top: 1rem;
        background-color: #007bff;
        color: white;
        border: none;
        padding: 0.5rem 1rem;
        border-radius: 6px;
        cursor: pointer;
    }

    .close-btn:hover {
        background-color: #0056b3;
    }
</style>

{#if visible}
    <div class="overlay" on:click={onClose}>
        <div class="modal" on:click|stopPropagation>
            <h2>가계부 작성하기</h2>
            <button class="close-btn" on:click={onClose}>닫기</button>
        </div>
    </div>
{/if}
