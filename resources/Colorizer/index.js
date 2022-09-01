const sharp = require("sharp");

const inputs = ["planks.png"];
const colors = [
    {
        name: "african_padauk",
        color: "#75261D",
    },
    {
        name: "alder",
        color: "#A9794A",
    },
    {
        name: "angelim",
        color: "#A1703E",
    },
    {
        name: "argyle_eucalyptus",
        color: "#F4A272",
    },
    {
        name: "bald_cypress",
        color: "#D6A86A",
    },
    {
        name: "baobab",
        color: "#C7AD80",
    },
    {
        name: "beech",
        color: "#DDB487",
    },
    {
        name: "black_walnut",
        color: "#664A29",
    },
];

inputs.forEach((input) => {
    colors.forEach((color) => {
        sharp(input).tint(color.color).modulate({ brightness: 0.95 }).toFile(`output/${color.name}.png`);
    });
});
