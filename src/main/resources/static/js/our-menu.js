"use strict";

const sampleSpecials = [
  {
    productId: 1,
    name: "Sample Soup and Salad",
    price: "5.99",
    categoryName: "Lunch",
    imageUrl:
      "https://res.cloudinary.com/dqboyjgbo/image/upload/f_webp/w_500/h_550/v1700133800/eknytrl58x8nxq4hpc3b.jpg",
    description: "A delightful sample soup paired with a fresh salad.",
  },
  {
    productId: 2,
    name: "Sample Ice Cream Sundae",
    price: "4.99",
    categoryName: "Dessert",
    imageUrl:
      "https://res.cloudinary.com/dqboyjgbo/image/upload/f_webp/w_500/h_550/v1700138122/dzxujcw3iea4m640gi5r.jpg",
    description: "Sample sundae with delicious toppings and syrup.",
  },
  {
    productId: 3,
    name: "Sample Wrap",
    price: "6.50",
    categoryName: "Lunch",
    imageUrl:
      "https://res.cloudinary.com/dqboyjgbo/image/upload/f_webp/w_500/h_550/v1700134013/wdoqex3nqomc9wx58aft.jpg",
    description:
      "A sample wrap filled with an assortment of fresh ingredients.",
  },
];

function fetchSpecials() {
  fetch(
    "https://domani1.salmonwater-6e46125d.westeurope.azurecontainerapps.io/specialties",
  )
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then((specials) => updatePage(specials))
    .catch((error) => {
      console.error("Error fetching specials:", error);

      // Fallback to sample data on error
      updatePage(sampleSpecials);
    });
}

function createSpecialItemHtml(item) {
  return `
    <div class="menu-item">
      <img src="${item.imageUrl}" alt="${item.name}" onerror="this.onerror=null;this.src='default_fallback_image.jpg';">
      <div class="menu-info">
        <h3>${item.name}</h3>
        <p class="price">${item.price}</p>
        <p class="description">${item.description}</p>
      </div>
    </div>
  `;
}

function updatePage(specials) {
  const menuItemsContainer = document.getElementById("menu-items");
  menuItemsContainer.innerHTML = specials.map(createSpecialItemHtml).join("");
}

fetchSpecials();

setInterval(fetchSpecials, 3000);
