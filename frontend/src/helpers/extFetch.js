export async function extFetch(url, method, body) {
  if (url.startsWith('http://localhost/%27')) {
    console.warn("Do not fetch http://localhost:5001/rest/etc, just write: '/rest/etc'")
  }
  const result = await fetch(url, {
    method,
    body: body ? JSON.stringify(body) : undefined,
    headers: { 'content-type': 'application/json' },
  })

console.log("url:", url);

  if (url.includes("login")) {
  console.log("In extFetch, trying to login..., result.ok:", result.ok, ", status:", result.status);
    let a = await result.json();
    console.log("In extFetch, after trying to log in, a:", a);
    return a;
  }

  if (result.ok) {
    return result.json()
  }
    return false
}