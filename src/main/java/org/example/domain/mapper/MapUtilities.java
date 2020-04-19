package org.example.domain.mapper;

public class MapUtilities
{
    public static <T> T nullCheck(T one, T two)
    {
        return one != null ? one : two;
    }
}
