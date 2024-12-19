/**
 * @param {number} current
 * @param {number} start
 * @param {number} end
 * @param {number} last
 * @param {(i: number) => any} handler
 */

function Paginator(current, start, end, last, handler) {
  const ul = document.createElement("ul");
  ul.classList.add("pagination", "justify-content-center");

  if (start > 1) {
    ul.append(_li(1))
    ul.append(_ctdot())
  }

  for (let i = start; i <= end; i += 1) {
    const li = _li(i)
    if (i == current) li.classList.add("active");
    ul.append(li)
  }

  if (end < last) {
    ul.append(_ctdot())
    ul.append(_li(last))
  }

  ul.addEventListener("click", e => {
    const found = findLi(e.target)
    if (found) {
      const page = found.dataset.page
      if (page) handler(page)
    }
  })
  return ul
}

function _ctdot() {
  const li = document.createElement("li");
  li.classList.add("page-item", "disabled");

  li.innerHTML = '<a class="page-link">&ctdot;</a>'
  return li
}


/**
 * @param {number} pg
 * @param {string | undefined} href
 * @private
 */
function _li(pg, href) {
  const li = document.createElement("li");
  li.classList.add("page-item");
  li.dataset.page = pg;

  const anchor = document.createElement("a");
  anchor.classList.add("page-link");

  anchor.textContent = pg;

  if (href) anchor.href = href;

  li.append(anchor)

  return li
}


/**
 * @param {HTMLElement} target
 */
function findLi(target) {
  while (target != null) {
    if (target.tagName.toLowerCase() === "li" && target.classList.contains('page-item')) return target;
    target = target.parentElement
  }
}