<script>
  let amount = '';
  let category = '지출';
  let assetType = '';
  let dateTime = '';
  let assetOptions = ['현금', '체크카드', '신용카드', '계좌이체', '기타'];
</script>

<style>
  .container {
    max-width: 600px;
    margin: 2rem auto;
    padding: 1rem;
    border: 1px solid #ccc;
    border-radius: 8px;
    font-family: sans-serif;
  }

  h2 {
    text-align: center;
    margin-bottom: 1rem;
  }

  label {
    display: block;
    margin-top: 1rem;
    font-weight: bold;
  }

  input, select {
    width: 100%;
    padding: 0.5rem;
    margin-top: 0.5rem;
    box-sizing: border-box;
  }

  .category-group {
    display: flex;
    justify-content: space-between;
    margin-top: 0.5rem;
  }

  .category-group label {
    flex: 1;
    text-align: center;
  }

  button {
    margin-top: 2rem;
    width: 100%;
    padding: 0.75rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 1rem;
  }

  nav {
    background-color: #f8f8f8;
    padding: 1rem;
    display: flex;
    justify-content: space-around;
    border-bottom: 1px solid #ddd;
    font-weight: bold;
  }

  nav a {
    text-decoration: none;
    color: #333;
  }
</style>

<nav>
  <a href="#">대시보드</a>
  <a href="#">가계부</a>
  <a href="#">자산</a>
  <a href="#">그룹</a>
</nav>

<div class="container">
  <h2>가계부 기록 추가</h2>

  <label for="amount">금액 (₩)</label>
  <input id="amount" type="number" bind:value={amount} />

  <label>분류</label>
  <div class="category-group">
    <label><input type="radio" name="category" value="지출" bind:group={category} /> 지출</label>
    <label><input type="radio" name="category" value="수입" bind:group={category} /> 수입</label>
    <label><input type="radio" name="category" value="이체" bind:group={category} /> 이체</label>
  </div>

  <label for="assetType">자산 종류</label>
  <select id="assetType" bind:value={assetType}>
    <option value="" disabled selected>선택하세요</option>
    {#each assetOptions as option}
      <option value={option}>{option}</option>
    {/each}
  </select>

  <label for="dateTime">일시</label>
  <input id="dateTime" type="datetime-local" bind:value={dateTime} />

  <button on:click={() => alert('기록이 추가되었습니다.')}>추가</button>
</div>