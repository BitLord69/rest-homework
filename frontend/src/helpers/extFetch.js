export async function extFetch(url, method, body) {
  if (url.startsWith('http://localhost/%27')) {
    console.warn("Do not fetch http://localhost:5001/rest/etc, just write: '/rest/etc'")
  }
  const result = await fetch(url, {
    method,
    body: body ? JSON.stringify(body) : undefined,
    headers: { 'content-type': 'application/json' },
  })

  if (result.ok) {
    return result.json()
  }
    return false
}