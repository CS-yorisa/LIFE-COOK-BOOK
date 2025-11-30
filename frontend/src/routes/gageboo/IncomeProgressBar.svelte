<script>
    export let percent = 0; // 0~100 사이의 값
</script>

<style>
    .bar-container {
        width: 100%;
        height: 24px;
        background-color: #FFF0F6;
        overflow: hidden;
    }

    .bar-fill {
        height: 100%;
        background-color: #FF4890;
        transition: width 0.3s ease;
    }

    .label {
        margin-top: 0.5rem;
        text-align: center;
        font-weight: bold;
    }
</style>

<div class="bar-container">
    <div class="bar-fill" style="width: {percent}%"></div>
</div>
