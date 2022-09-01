const sharp = require("sharp");

const inputs = ["blue.png"];
const colors = [
    {
        name: "black",
        color: "#16171B",
    },
    {
        name: "blue",
        color: "#335AD1",
    },
    {
        name: "brown",
        color: "#734829",
    },
    {
        name: "cyan",
        color: "#158A91",
    },
    {
        name: "gray",
        color: "#3F4548",
    },
    {
        name: "green",
        color: "#556E1C",
    },
    {
        name: "light_blue",
        color: "#3BB0DA",
    },
    {
        name: "lime",
        color: "#71B91A",
    },
    {
        name: "magenta",
        color: "#BE46B4",
    },
    {
        name: "orange",
        color: "#F17717",
    },
    {
        name: "pink",
        color: "#D05FA1",
    },
    {
        name: "purple",
        color: "#A81DA3",
    },
    {
        name: "red",
        color: "#A7242F",
    },
    {
        name: "silver",
        color: "#8E8E87",
    },
    {
        name: "white",
        color: "#EAEDED",
    },
    {
        name: "yellow",
        color: "#D9CA43",
    },
];

inputs.forEach((input) => {
    colors.forEach((color) => {
        sharp(input).tint(color.color).modulate({ brightness: 0.95 }).toFile(`output/${color.name}.png`);
    });
});
